package service

import (
	"model"
	"dao"
	"dto"
	"deliverer"
	"assembler"
)

func Tracing(tracingDevice dto.TracingDeviceDTO) (id_user string, err error) {

	var deviceFinder model.DeviceFinder
	
	deviceFinder.Id_user = tracingDevice.Id_user
	device, err := dao.FindDevice(deviceFinder)
	deviceDTO := assembler.ToDeviceDTO(device)	

	if err == nil {
		if deviceDTO.Followers == 0 {
			err = deliverer.RegisterDevice(deviceDTO)
		}

		deviceDTO.Followers += 1
		device := assembler.FromDeviceDTO(deviceDTO)
		err = dao.UpdateDevice(deviceFinder,&device)

		id_user = deviceDTO.Id_user
	}

	return
}

func StopTracing(tracingDevice dto.TracingDeviceDTO) (id_user string, err error) {
	var deviceFinder model.DeviceFinder

	deviceFinder.Id_user = tracingDevice.Id_user
	device, err := dao.FindDevice(deviceFinder)
	deviceDTO := assembler.ToDeviceDTO(device)
	if err == nil {
		deviceDTO.Followers -= 1
		if deviceDTO.Followers == 0 {
			deliverer.UnregisterDevice(deviceDTO)
		}
		device := assembler.FromDeviceDTO(deviceDTO)
		err = dao.UpdateDevice(deviceFinder, &device)
		
		id_user = deviceDTO.Id_user
	}

	return
}