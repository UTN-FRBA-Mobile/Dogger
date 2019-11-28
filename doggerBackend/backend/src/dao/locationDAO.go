package dao

import (
	"gopkg.in/mgo.v2"
	"model"
	"fmt"
	"reflect"
	"strings"
)

const (
	LOCATION_COLLECTION string = "location"
)

var cLocation *mgo.Collection

func InitLocationDAO(database *mgo.Database) {
	fmt.Println("InitLocationDAO")
	cLocation = database.C(LOCATION_COLLECTION)
}

func InsertLocation(userLocation *model.UserLocation) (err error) {
	err = cLocation.Insert(userLocation)
	return
}

func UpdateLocation(locationFinder model.LocationFinder, userLocation *model.UserLocation) error {
	return cLocation.Update(locationFinder, userLocation)
}

func GetAllLocations(filterLocation model.FilterLocation) (locations []model.UserLocation, err error) {
	filtro:=ToFilter(filterLocation)
	err = cLocation.Find(filtro).All(&locations)
	return
}

func FindLocation(locationFinder model.LocationFinder) (userLocation model.UserLocation, err error) {
	err = cLocation.Find(locationFinder).One(&userLocation)
	return
}

func ToFilter(aStruct interface{}) (aMap map[string]interface{}) {
	values := reflect.ValueOf(aStruct)
	fields:= reflect.TypeOf(aStruct)
	num := fields.NumField()
	aMap = make(map[string]interface{})
	for i := 0; i < num; i++ {
		evaluate := values.Field(i).Interface()
		if !reflect.DeepEqual( evaluate , reflect.Zero(reflect.TypeOf(evaluate)).Interface()) {
	    		aMap[strings.ToLower(fields.Field(i).Name)] = evaluate
		}
	}
	return
}

