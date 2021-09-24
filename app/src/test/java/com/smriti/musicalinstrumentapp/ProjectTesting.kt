package com.smriti.musicalinstrumentapp

import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.entity.BookingInstrument
import com.smriti.musicalinstrumentapp.entity.Product
import com.smriti.musicalinstrumentapp.entity.User
import com.smriti.musicalinstrumentapp.repository.BookingRepository
import com.smriti.musicalinstrumentapp.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ProjectTesting {
    @Test
    fun loginTesting() {
        runBlocking {
            val expectedResult = true
            val repository = UserRepository()
            var response = repository.checkUser("smreety", "1234")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun signUpTesting() {
        runBlocking {
            val expectedResult = true
            val repository = UserRepository()
            var user = User(
                name = "Xitiz kafle",
                username = "xitiz",
                email = "xitiz@gmail.com",
                password = "hola",
                phone = "3764324",
                address = "pepsicola"
            )
            var response = repository.registerUser(user)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun addToCartTesting() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            ServiceBuilder.token = "Bearer " + repo.checkUser("smreety", "1234").token
            var repository = BookingRepository()
            var booking = BookingInstrument(
                product_id = Product(_id = "60780563fddfe22d4cf380ac"),
                quantity = 1,
                price = 750
            )
            var response = repository.bookInstrument(booking)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)

        }
    }
    @Test
    fun cartTesting() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            ServiceBuilder.token = "Bearer " + repo.checkUser("smreety", "1234").token
            var repository = BookingRepository()
            var response=repository.retrieveBooking()
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun deleteItemsTesting(){
        runBlocking {
            var expectedResult=true
            val repo = UserRepository()
            ServiceBuilder.token="Bearer " + repo.checkUser("smreety","1234").token
            var repository=BookingRepository()
            var response=repository.deleteBooking("607c55538bcf17323cd22e70")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun loginTestings() {
        runBlocking {
            val expectedResult = false
            val repository = UserRepository()
            var response = repository.checkUser("smreety", "1234")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun registrationTestings() {
        runBlocking {
            val expectedResult = false
            val repository = UserRepository()
            var user = User(
                    name = "Prakriti kafle",
                    username = "prakriti",
                    email = "prakriti@gmail.com",
                    password = "hello",
                    phone = "98646325",
                    address = "Bhaktapur"
            )
            var response = repository.registerUser(user)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun addtocartTestings() {
        runBlocking {
            val expectedResult = true
            val repo = UserRepository()
            ServiceBuilder.token = "Bearer " + repo.checkUser("smreety", "1234").token
            var repository = BookingRepository()
            var booking = BookingInstrument(
                    product_id = Product(_id = "6078070dfddfe22d4cf380ad"),
                    quantity = 1,
                    price = 750

            )
            var response = repository.bookInstrument(booking)
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)

        }
    }
    @Test
    fun cartTestings() {
        runBlocking {
            val expectedResult = false
            val repo = UserRepository()
            ServiceBuilder.token = "Bearer " + repo.checkUser("smreety", "1234").token
            var repository = BookingRepository()
            var response=repository.retrieveBooking()
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun deleteTestings(){
        runBlocking {
            var expectedResult=true
            val repo = UserRepository()
            ServiceBuilder.token="Bearer " + repo.checkUser("smreety","1234").token
            var repository=BookingRepository()
            var response=repository.deleteBooking("607c55538bcf17323cd22e70")
            var actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }







}
