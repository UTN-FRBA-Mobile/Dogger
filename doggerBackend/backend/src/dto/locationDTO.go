package dto

import (
)

type SignatureLocationDTO struct {
	Id_user		string		`json:"id_user" bson:"id_user" binding:"required"`
	Longitude	float64		`json:"longitude" bson:"longitude" binding:"required"`
	Latitude	float64		`json:"latitude" bson:"latitude" binding:"required"`
}

type SignatureFilterLocationDTO struct {
	Id_user		string		`form:"id_user"`
}

type UserLocationDTO struct {
	Id_user		string		`json:"id_user"`
	Location 	LocationDTO `json:"Location"`
}

type LocationDTO struct {
	Longitude	float64		`json:"longitude"	bson:"longitude"`
	Latitude	float64		`json:"latitude"	bson:"latitude"`
}

type FilterLocationDTO struct {
	Id_user		string
}