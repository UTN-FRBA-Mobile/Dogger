package assembler

import (
	"dto"
	"model"
)

type UserLocation struct {
	Id_user		string	`json:"id_user" bson:"id_user"`
	Location 	Location `json:"location" bson:"location"`
}

type Location struct {
	Longitude	string	`json:"longitude" bson:"longitude"`
	Latitude	string	`json:"latitude" bson:"latitude"`
}

func FromUserLocationDTO(uLocationDTO dto.UserLocationDTO) (uLocation model.UserLocation) {
	uLocation.Id_user = uLocationDTO.Id_user
	uLocation.Location.Type = "Point"
	uLocation.Location.Coordinates = [2]float64{ uLocationDTO.Location.Longitude, uLocationDTO.Location.Latitude }
	return
}

func FromFilterLocationDTO(filterLocationDTO dto.FilterLocationDTO) (filterLocation model.FilterLocation) {
	filterLocation.Id_user = filterLocationDTO.Id_user
	return
}

func ToUsersLocationsDTO(usersLocations []model.UserLocation) (usersLocationsDTO []dto.UserLocationDTO) {
	for _, userLocation := range usersLocations {
		usersLocationsDTO = append(usersLocationsDTO, ToUserLocationDTO(userLocation))
	}
	return
}

func ToUserLocationDTO(userLocation model.UserLocation) (userLocationDTO dto.UserLocationDTO){
	userLocationDTO.Id_user	 			= userLocation.Id_user
	userLocationDTO.Location.Longitude 	= userLocation.Location.Coordinates[0]
	userLocationDTO.Location.Latitude 	= userLocation.Location.Coordinates[1]
	return
}

func FromUpsertLocationDTO(upsertLocation dto.UpsertLocationDTO) (mLocationFinder model.LocationFinder){
	mLocationFinder.Id_user = upsertLocation.Id_user
	return
}
