package controller

import (
	"dto"
	"github.com/gin-gonic/gin"
	"net/http"
	"service"
)

func NewTracing(c *gin.Context) {
	var tracingDevice dto.TracingDeviceDTO
	err := c.BindJSON(&tracingDevice)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Bad request",
		})
		return
	}

	if tracingDevice.Follow {
		userOnTrack, err := service.Tracing(tracingDevice)

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{
				"error": "The devices could not be registered",
			})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"user": userOnTrack,
		})

	} else {
		userOffTrack, err := service.StopTracing(tracingDevice)

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{
				"error": "The devices could not be unregistered",
			})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"user": userOffTrack,
		})

	}
	
	return

}
