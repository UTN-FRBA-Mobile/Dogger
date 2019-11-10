package router

import (
	"controller"
	"github.com/gin-gonic/gin"
)

func InitLocationRoutes(locationGroup *gin.RouterGroup) {
	locationGroup.GET("/", controller.GetAllLocations)
	locationGroup.POST("/", controller.UpdateLocation)
}
