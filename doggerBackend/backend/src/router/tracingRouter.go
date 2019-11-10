package router

import (
	"controller"
	"github.com/gin-gonic/gin"
)

func InitTracingRoutes(tracingGroup *gin.RouterGroup) {
	tracingGroup.POST("/", controller.NewTracing)
}
