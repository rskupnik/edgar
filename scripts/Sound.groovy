import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

class Sound {

    String[] phrase() {
        ["daj głos"]
    }

    String act() {
        GpioController gpioController = GpioFactory.getInstance()
        GpioPinDigitalOutput pin22 = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_03)


        try {
            pin22.high()
            System.out.println("pi")
            Thread.sleep(100)
            System.out.println("pi")
            pin22.low()
            Thread.sleep(100)
            System.out.println("pi")
            pin22.high()
            Thread.sleep(100)
            System.out.println("pi")
            pin22.low()
            Thread.sleep(100)
            System.out.println("pi")
            pin22.high()
            Thread.sleep(100)
            System.out.println("pi")
            pin22.low()
        } catch (InterruptedException e) {

        }

        pin22.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF)
    }
}
