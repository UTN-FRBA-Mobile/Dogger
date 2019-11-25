package controller

import (
	"dto"
	"github.com/gin-gonic/gin"
	"net/http"
	"service"
)

func UpdateLocation(c *gin.Context) {

	var sLocation dto.SignatureLocationDTO
	err := c.BindJSON(&sLocation)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Bad request",
		})
		return
	}

	var userLocation dto.UpsertLocationDTO
	userLocation.Id_user = sLocation.Id_user
	userLocation.Location.Longitude = sLocation.Longitude
	userLocation.Location.Latitude = sLocation.Latitude

	err = service.UpdateLocation(userLocation)

	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "the location could not be updated",
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "the location has been updated successfully",
	})
}

func GetAllLocations(c *gin.Context) {

	var sFilterLocation dto.SignatureFilterLocationDTO
	err := c.Bind(&sFilterLocation)
	
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Bad request",
		})
		return
	}

	var LocationFilter dto.FilterLocationDTO
	LocationFilter.Id_user = sFilterLocation.Id_user
	
	locations, err := service.GetAllLocations(LocationFilter)

	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "Can not get the data",
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"data": locations,
	})

}
