package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Reservation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 예약 정보 전체 조회 API ([GET] /api/v1/reserve/consult/data) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("ReservationResponse")
public class HospitalReservationRes extends BaseResponseBody{
    @ApiModelProperty(name = "Appoint ID")
    int appointId;
    String time;
    String diagnosis;
    String note;
    int type;
//    int isDelete;
//    String licenseNumber;
    int hospitalNo;
//    String userId;
    List<Reservation> result;

    public static HospitalReservationRes ofHospital(Integer statusCode, String message, Reservation reservation) {
        HospitalReservationRes res = new HospitalReservationRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAppointId(reservation.getAppointId());
        res.setTime(reservation.getTime());
        res.setHospitalNo(reservation.getHospitalNo());
        res.setNote(reservation.getNote());
        res.setDiagnosis(reservation.getDiagnosis());
        res.setType(reservation.getType());
        // 반환하지 않아도 되는 데이터
//        res.setType(reservation.getType());
//        res.setIsDelete(reservation.getIsDelete());
//        res.setUserId(reservation.getUserId());
//        res.setLicenseNumber(reservation.getLicenseNumber());

        return res;
    }

    public static HospitalReservationRes ofHospital(Integer statusCode, String message, List<Reservation> reservation) {
        HospitalReservationRes res = new HospitalReservationRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.result = reservation;
        return res;
    }

//    public static List<HospitalReservationRes> listOfHospital(List<Reservation> reservations) {
//        return reservations.stream().map(HospitalReservationRes::ofHospital).collect(Collectors.toList());
//    }
}
