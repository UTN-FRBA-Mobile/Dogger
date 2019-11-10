package dto

import (
)

type SignatureDeviceDTO struct {
	Id_device	string	`json:"id_device" bson:"id_device"`
}

type DeviceDTO struct {
	Id_device	string	`json:"id_device" bson:"id_device"`
	Id_user		string  `json:"id_user" bson:"id_user"`
	Followers	int    `json:"followers" bson:"followers"`
}

type UpsertDeviceDTO struct {
	Id_device	string	`json:"id_device" bson:"id_device"`
	Id_user		string  `json:"id_user" bson:"id_user"`
}
