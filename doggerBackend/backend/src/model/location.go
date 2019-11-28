package model

import (
)

type UserLocation struct {
	Id_user		string		`json:"id_user" bson:"id_user"`
	Location 	Location	`json:"location" bson:"location"`
}

type Location struct {
	Type		string
	Coordinates [2]float64
}

type FilterLocation struct {
	Id_user		string		`json:"id_user" bson:"id_user"`
}

type LocationFinder struct {
	Id_user			string	`json:"id_user" bson:"id_user"`
}