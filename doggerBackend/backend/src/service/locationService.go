package service

import (
	"dao"
	"dto"
	"assembler"
	"model"
	"fmt"
)

func InsertLocation(location dto.UserLocationDTO) (err error) {
	var mLocation model.UserLocation
	mLocation = assembler.FromUserLocationDTO(location)
	err = dao.InsertLocation(&mLocation)
	return
}

func UpdateLocation(upsertLocation dto.UpsertLocationDTO) (err error) {
	var mLocationFinder model.LocationFinder

	mLocationFinder = assembler.FromUpsertLocationDTO(upsertLocation)

	var userLocation model.UserLocation
	userLocation, err = dao.FindLocation(mLocationFinder)
	userLocation.Id_user = upsertLocation.Id_user
	userLocation.Location.Type = "Point"
	userLocation.Location.Coordinates = [2]float64{ upsertLocation.Location.Longitude, upsertLocation.Location.Latitude }

	fmt.Println("LLEGA")
	if err != nil {
		fmt.Println("INSERT")
		err = dao.InsertLocation(&userLocation)
	} else {
		fmt.Println("UPDATE")
		err = dao.UpdateLocation(mLocationFinder, &userLocation)
	}
	return
}

func GetAllLocations(filterLocation dto.FilterLocationDTO) (usersLocationsDTO []dto.UserLocationDTO, err error) {
	var mFilterLocation model.FilterLocation
	var mUserLocations []model.UserLocation
	
	mFilterLocation = assembler.FromFilterLocationDTO(filterLocation)
	fmt.Println("Service mFilterLocation", mFilterLocation)
	mUserLocations, err = dao.GetAllLocations(mFilterLocation)
	fmt.Println("Service mUserLocations", mUserLocations)
	usersLocationsDTO = assembler.ToUsersLocationsDTO(mUserLocations)
	return
}