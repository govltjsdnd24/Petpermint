<script setup>
import { ref, reactive, computed, onBeforeMount } from 'vue'

// import dynamics from 'dynamics.js'
import { useRouter } from 'vue-router'
import { useReserveStore } from '@/stores/reserve'
import { useAccountStore } from '@/stores/account'
import { storeToRefs } from 'pinia'
const accountstore = useAccountStore()
const reservestore = useReserveStore()
const { mybook, appointId } = storeToRefs(reservestore)
const { userdata } = storeToRefs(accountstore)
const router = useRouter()

const handleEnteringConsultButton = (e) => {
  dialog.value = false
  router.push({ name: 'cam-consult', params: { appointId: appointId.value } })
}

const sideWidth = 110

let isDragging = false
const start = { x: 0, y: 0 }
const c = reactive({ x: sideWidth / 2, y: 0 })

const headerPath = computed(() => {
  const radius = 20
  const width = c.x
  const height = 130
  return `M0,0
          H${width - radius} Q${width},${0} ${width},${radius}
          V${height - radius} Q${width},${height} ${width - radius},${height}
          H0 Z`
})

const textVisibleOnClick = ref(true)

// const contentPosition = computed(() => {
//   const dx = c.x - sideWidth / 2
//   const dampen = dx > 0 ? 2 : 4
//   return {
//     transform: `translate(${dx / dampen}px, 0)`
//   }
// })

// function startDrag(e) {
//   e = e.changedTouches ? e.changedTouches[0] : e
//   isDragging = true
//   start.x = e.pageX
//   start.y = e.pageY
//   textVisibleOnClick.value = false
// }
const dialog = ref(false)
// Open dialog method
function openDialog() {
  dialog.value = true
}

// Close dialog method
function closeDialog() {
  dialog.value = false
}
// function onDrag(e) {
//   //console.log('Dragging')
//   e = e.changedTouches ? e.changedTouches[0] : e
//   if (isDragging) {
//     const dx = e.pageX - start.x
//     const dampen = dx > 0 ? 1.5 : 4
//     c.x = sideWidth / 2 + dx / dampen
//     if (Math.abs(dx) > 40) {
//       //console.log('Opening dialog')
//       stopDrag()
//       dialog.value = true
//     }
//   }
// }

// function stopDrag() {
//   if (isDragging) {
//     isDragging = false
//     textVisibleOnClick.value = true
//     dynamics.animate(
//       c,
//       { x: sideWidth / 2, y: 0 },
//       { type: dynamics.spring, duration: 700, friction: 280 }
//     )
//   }
// }
// function toggleTextVisibility() {
//   textVisibleOnClick.value = !textVisibleOnClick.value
// }

const showHoverText = ref(false)
// const handleMouseEnter = () => {
//   showHoverText.value = true
// }
// const handleMouseLeave = () => {
//   stopDrag()
//   showHoverText.value = false
// }
const type = ref()
onBeforeMount(async () => {
  await reservestore.getmybook()
  await accountstore.getnormalprofile(localStorage.getItem('useremail'))
  //console.log(mybook.value.appointId)
  type.value = localStorage.usertype
})
const isUser = ref(localStorage.getItem('usertype') == 1 ? true : false)
</script>

<template>
  <div>
    <div class="draggable" @click="openDialog" v-if="isUser == true">
      <svg class="bg" width="100" height="200">
        <path :d="headerPath" fill="#d2e0fb"></path>
      </svg>
      <div class="header">My 예약카드</div>
    </div>
    <!-- <div v-if="showHoverText && textVisibleOnClick" class="text-rot">
      오른쪽으로 잡아당기세요
      <div class="arrow">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div> -->
    <v-dialog v-model="dialog" width="400px">
      <!-- 온라인 초기 상담 -->
      <v-carousel height="480" hide-delimiter-background show-arrows="hover" color="#668BA7">
        <v-carousel-item v-if="mybook.message == 'Success'">
          <div class="card">
            <div class="card__img">
              <svg width="100%">
                <rect x="0" y="0" fill="#668BA7" width="100%" height="50%"></rect>
              </svg>
            </div>

            <!-- 강아지 프로필 사진 -->
            <div class="card__avatar">
              <img
                src="/assets/img/default_profile.png "
                style="width: 80%; height: auto; border-radius: 93.75px"
              />
            </div>
            <v-row class="rows">
              <v-col col="12" md="12" class="margins" style="height: 10px">
                <div class="card__content">
                  <div class="card__title">보호자</div>
                  <div class="card__subtitle">{{ userdata.userName }}</div>
                </div>
              </v-col>
              <v-col col="12" md="12" class="margins" style="height: 10px">
                <div class="card__content">
                  <div class="card__title">반려동물 이름</div>
                  <div class="card__subtitle">{{ mybook.animalName }}</div>
                </div></v-col
              >
              <v-col col="12" md="12" class="margins" style="height: 10px">
                <div class="card__content">
                  <div class="card__title">예약 유형</div>
                  <div class="card__subtitle">온라인 초기 상담</div>
                </div>
              </v-col>
              <v-col col="12" md="12" class="margins" style="height: 10px">
                <div class="card__content">
                  <div class="card__title">예약 날짜</div>
                  <div class="card__subtitle">{{ mybook.time }}</div>
                </div>
              </v-col>
              <v-col col="12" md="12" class="margins" style="height: 10px">
                <div class="card__content">
                  <div class="card__title">수의사 / 병원</div>
                  <div class="card__subtitle">{{ mybook.doctorName }}</div>
                </div>
              </v-col>
              <v-col col="12" md="3" class="margins">
                <div class="card__title" style="margin-right: 0">메모</div>
              </v-col>
              <v-col col="12" md="9" class="margins">
                <div class="card__subtitle">
                  {{ mybook.note }}
                </div>
              </v-col>
            </v-row>
          </div>

          <!-- 만약 온라인 초기 상담 시간이 안되었다면 disabled를 넣어서 클릭 안되게하기
          <v-btn class="fixed-top-right" variant="flat" color="#C0A0A8" disabled
            >상담 입장하기</v-btn
          > -->
          <v-btn
            class="fixed-top-right"
            variant="flat"
            color="#C0A0A8"
            @click="handleEnteringConsultButton"
            >상담 입장하기</v-btn
          >

          <!-- 닫기 버튼 -->
          <button class="closed" @click="closeDialog">
            <v-icon icon="mdi-close-thick"></v-icon>
          </button>
        </v-carousel-item>
        <!-- 병원 상담 -->
        <v-carousel-item v-else>
          <div class="card">
            <div class="card__img">
              <svg width="100%">
                <rect x="0" y="0" fill="#668BA7" width="100%" height="50%"></rect>
              </svg>
            </div>

            <!-- 강아지 프로필 사진 -->
            <div class="card__avatar">
              <img
                src="/assets/img/default_profile.png "
                style="width: 80%; height: auto; border-radius: 93.75px"
              />
            </div>

            <v-row class="rows">
              <p>예약 내역이 존재하지 않습니다.</p>
            </v-row>
          </div>
          <!-- 닫기 버튼 -->
          <button class="closed" @click="closeDialog">
            <v-icon icon="mdi-close-thick"></v-icon>
          </button>
        </v-carousel-item>
      </v-carousel>
    </v-dialog>
  </div>
</template>

<style scoped>
.closed {
  position: absolute;
  top: 0;
  height: 40px;
  left: 105%;
  transform: translateX(-50%);
  z-index: 15;
  border-top-right-radius: 20px;
}
.fixed-top-right {
  position: fixed;
  top: 100px;
  right: 5px;
  z-index: 1000;
}
.rows {
  margin-left: 20px;
  margin-right: 20px;
  padding-bottom: 10px;
  margin-top: -40px;
}
.margins {
  padding-top: 0px;
  padding-bottom: 0px;
}
.card__content {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.card__title {
  margin-right: 20px;
  font-weight: bold;
  font-size: 17px;
  color: var(--main-color);
}

.card__subtitle {
  font-weight: bold;
  font-size: 17px;
  color: var(--submain-color);
}

.card {
  --main-color: #000;
  --submain-color: #78858f;
  --bg-color: #fff;
  font-family:
    system-ui,
    -apple-system,
    BlinkMacSystemFont,
    'Segoe UI',
    Roboto,
    Oxygen,
    Ubuntu,
    Cantarell,
    'Open Sans',
    'Helvetica Neue',
    sans-serif;
  position: relative;
  width: 400px;
  height: 480px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 20px;
  background: var(--bg-color);
}

.card__img {
  height: 192px;
  width: 100%;
}

.card__img svg {
  height: 100%;
  border-radius: 20px 20px 0 0;
}

.card__avatar {
  position: absolute;
  width: 114px;
  height: 114px;
  background: var(--bg-color);
  border-radius: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  top: calc(18% - 57px);
}

.card__avatar svg {
  width: 100px;
  height: 100px;
}

.card__btn {
  margin-top: 15px;
  width: 76px;
  height: 31px;
  border: 2px solid var(--main-color);
  border-radius: 4px;
  font-weight: 700;
  font-size: 11px;
  color: var(--main-color);
  background: var(--bg-color);
  text-transform: uppercase;
  transition: all 0.3s;
}

.card__btn-solid {
  background: var(--main-color);
  color: var(--bg-color);
}

.card__btn:hover {
  background: var(--main-color);
  color: var(--bg-color);
}

.card__btn-solid:hover {
  background: var(--bg-color);
  color: var(--main-color);
}

.my-card {
  margin-bottom: 7px;
  color: #474747;
}

.div {
  z-index: 1000;
  position: fixed;
  top: 50%;
  left: 0;
  transform: translateY(-120%) translateX(20%);
  width: 30px;
  height: 50;
  background-color: rgb(255, 0, 0);
}
.text-rot {
  z-index: 1000;
  position: fixed;
  top: 50%;
  left: 0;
  transform: translateY(-120%) translateX(20%);
  font-weight: bold;
  color: #868484;
}

.draggable {
  background-color: transparent;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  width: 60px;
  height: 130px;
  overflow: hidden;
  text-align: center;
  font-size: 14px;
  font-weight: 300;
  user-select: none;
  box-shadow: none;
  cursor: pointer;
  z-index: 1000;
  position: fixed;
  top: 50%;
  left: 0;
  transform: translateY(-50%);
}
.bg {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
}
.header,
.content {
  position: relative;
  z-index: 1;
  box-sizing: border-box;
}
.header {
  width: 200px;
  color: #565656;
  height: 50px;
  font-size: 19px;
  font-weight: bold;
  transform: rotate(90deg) translateY(170%) translateX(20%);
}
.arrow {
  position: absolute;
  top: 120%;
  left: 24%;
  transform: translate(-50%, -50%);
  transform: rotate(-90deg);
  cursor: pointer;
}

.arrow span {
  display: block;
  width: 1.1vw;
  height: 1.1vw;
  border-bottom: 4px solid #868484;
  border-right: 4px solid #868484;
  transform: rotate(45deg);
  margin: -10px;
  animation: animate 2s infinite;
}

.arrow span:nth-child(2) {
  animation-delay: -0.2s;
}

.arrow span:nth-child(3) {
  animation-delay: -0.4s;
}

@keyframes animate {
  0% {
    opacity: 0;
    transform: rotate(45deg) translate(-20px, -20px);
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0;
    transform: rotate(45deg) translate(20px, 20px);
  }
}

button {
  width: 110px;
  height: 50px;
  cursor: pointer;
  display: flex;
  align-items: center;
  border-radius: 5px;
}
</style>
