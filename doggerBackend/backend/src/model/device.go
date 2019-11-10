package model

import (
)

type DeviceFinder struct {
	Id_user			string	`json:"id_user" bson:"id_user"`
}

type Device struct {
	Id_device		string	`json:"id_device" bson:"id_device"`
	Id_user			string	`json:"id_user" bson:"id_user"`
	Followers		int    `json:"followers" bson:"followers"`
}