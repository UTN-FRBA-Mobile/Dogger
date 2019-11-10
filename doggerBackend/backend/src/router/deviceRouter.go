package router

import (
	"controller"
	"github.com/gin-gonic/gin"
)

func InitDeviceRoutes(deviceGroup *gin.RouterGroup) {
	deviceGroup.PUT("/:id_user", controller.NewDevice)
	deviceGroup.GET("/", controller.GetAllDevices)
}
