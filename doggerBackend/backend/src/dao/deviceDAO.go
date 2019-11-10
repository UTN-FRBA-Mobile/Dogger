package dao

import (
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
	"model"
)

const (
	DEVICE_COLLECTION string = "device"
)

var cDevice *mgo.Collection

func InitDeviceDAO(database *mgo.Database) {
	cDevice = database.C(DEVICE_COLLECTION)
}

func FindDevice(deviceFinder model.DeviceFinder) (device model.Device, err error) {
	err = cDevice.Find(deviceFinder).One(&device)
	return
}

func CreateDevice(device *model.Device) error {
	return cDevice.Insert(device)
}

func UpdateDevice(deviceFinder model.DeviceFinder, device *model.Device) error {
	return cDevice.Update(deviceFinder,device)
}

func GetAllDevices() (devices []model.Device, err error) {
	err = cDevice.Find(bson.M{}).All(&devices)
	return
}