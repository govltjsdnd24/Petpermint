# Porting Manual

- **개발환경**
  - IntelliJ: IntelliJ IDEA 2023.3.2 (Ultimate Edition) [Build #IU-233.13135.103]
  - JDK: 11
  - Spring Boot : 2.4.5
  - Spring Boot JPA
  - AWS EC2
  - AWS S3
  - Mysql
  - Redis
  - VisualCode
  - Node 18.12.0
  - NVM
  - Vue2
  - Vue3
  - Google Cloud Platform - Compute Engine
- BackEnd

  - OpenVidu

    ```bash
    # apt를 최신화합니다.
    sudo apt update
    sudo apt upgrade

    # Openvidu를 설치합니다.
    sudo su
    cd /opt
    curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash



    #필수 포트를 개방합니다.
    ufw allow 22/tcp
    ufw allow 80/tcp
    ufw allow 443/tcp
    ufw allow 3478/tcp
    ufw allow 3478/udp
    ufw allow 40000-57000/tcp
    ufw allow 40000-57000/udp
    ufw allow 57001-65535/tcp
    ufw allow 57001-65535/udp

    #(FrontEnd SSL 적용 후 진행)
    #Openvidu를 설정합니다.
    cd ./openvidu
    nano .env

    //수정 사항
    //DOMAIN_OR_PUBLIC_IP={서버의 도메인/공개 IP 주소}
    //OPENVIDU_SECRET={Openvidu 비밀번호}
    //CERTIFICATE_TYPE=letsencrypt
    //LETSENCRYPT_EMAIL={이메일}
    //HTTPS_PORT={포트번호}

    #Openvidu를 실행합니다
    ./openvidu
    ```

  - WAS

    ```bash
    # apt를 최신화합니다.
    sudo apt update
    sudo apt upgrade

    # Backend clone
    git clone https://lab.ssafy.com/s10-webmobile1-sub2/S10P12B303.git
    cd S10P12B303/backend

    #(FrontEnd SSL 적용 후 진행)
    # SSL 키 할당
    cd /etc/letsencrypt/live/{{인증서 발급 시 설정한 도메인 폴더}}/
    openssl pkcs12 -export -inkey privkey.pem -in cert.pem -out /{{Backend 폴더 경로}}/src/main/resources/keystore.p12

    # JDK 11을 설치합니다
    sudo apt-get install openjdk-11-jdk

    # Gradlew에 권한을 부여합니다.
    chmod +x gradlew

    # BootRun을 실햄합니다
    ./gradlew bootRun
    ```

- FrontEnd

  ```bash

  # git 정보 입력 후 추후 다시 물어보지 않도록 설정
  git config --global credential.helper store

  # nvm 설치
  curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.37.2/install.sh | bash
  source ~/.bashrc

  # node 설정
  nvm install 18.12.0
  nvm use 18.12.0

  # frontend clone
  git clone https://lab.ssafy.com/s10-webmobile1-sub2/S10P12B303.git
  cd S10P12B303/frontend

  # frontend build
  npm run install
  npm run build

  # build 된 폴더 경로를 복사합니다.
  cd dist
  pwd

  # apt를 최신화합니다.
  sudo apt update -y && sudo apt upgrade -y

  # nginx를 설치합니다.
  sudo apt install nginx

  # 설정 파일 수정
  # a를 눌러 append 모드로 수정할 수 있고 복사했던 dist 경로를 root 뒤에 덮어씁니다.
  # root /home/리눅스계정이름/S10P12B303/frontend/dist;
  # esc 후 : 누르고 wq를 적고 enter 하여 저장합니다.
  sudo vi /etc/nginx/sites-available/default

  # nginx 재시작
  sudo systemctl reload nginx
  ```

  - https 적용

    - 외부 IP 고정
      - 인스턴스 종료 후 인스턴스 수정
        ![Untitled](Porting%20Manual/Untitled%204.png)
    - Domain 적용
      - 가비아 도메인 구매
      - GCP> 네트워크 서비스> Cloud DNS> 영역만들기
        NameServer 생성 후 가비아에서 NameServer 적용
        ![Untitled](Porting%20Manual/Untitled%205.png)
    - SSL 적용

      ```bash
      sudo apt-get update

      sudo apt-get install certbot

      sudo apt-get install python3-certbot-nginx

      sudo vi /etc/nginx/sites-available/default
      # 아래 이미지처럼 domain을 적고 두번째 이미지처럼 index를 설정합니다.

      sudo service nginx restart
      ```

      ![Untitled](Porting%20Manual/Untitled%206.png)
      ![Untitled](Porting%20Manual/Untitled%207.png)
