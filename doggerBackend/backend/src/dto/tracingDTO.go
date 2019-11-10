package dto

import (
)

type TracingDeviceDTO struct {
	Id_user			string		`json:"id_user" bson:"id_user" binding:"required"`
	Follow			bool		`json:"follow" bson:"follow"`
}
