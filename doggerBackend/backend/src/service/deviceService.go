package service

import (
	"dao"
	"dto"
	"model"
	"fmt"
	"assembler"
)

func FindDevice(id_user string, app string) (completeDevice model.Device, err error) {
	fmt.Println("completeDevice2",completeDevice)
	return
}

func CreateDevice(upsertDevice dto.UpsertDeviceDTO) (err error) {
	var mDeviceFinder model.DeviceFinder

	mDeviceFinder = assembler.FromUpsertDeviceDTO(upsertDevice)

	var device model.Device
	device, err = dao.FindDevice(mDeviceFinder)
	device.Id_device = upsertDevice.Id_device

	if err != nil {
		device.Id_user = upsertDevice.Id_user
		err = dao.CreateDevice(&device)
	} else {
		err = dao.UpdateDevice(mDeviceFinder, &device)
	}
	return
}

func GetAllDevices() (devicesDTO []dto.DeviceDTO, err error) {
	var devices []model.Device
	devices, err = dao.GetAllDevices()
	devicesDTO = assembler.ToDevicesDTO(devices)
	return
}