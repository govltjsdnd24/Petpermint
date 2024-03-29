package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("EmergencyReservationInitResponse")
public class EmergencyReservationInitRes extends BaseResponseBody{
	@ApiModelProperty(name="User ID")
	String address;

	public static EmergencyReservationInitRes of(Integer statusCode, String message, String address) {
		EmergencyReservationInitRes res = new EmergencyReservationInitRes();
		res.setMessage(message);
		res.setStatusCode(statusCode);
		res.setAddress(address);
		return res;
	}
}
