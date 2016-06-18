import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

class LightBlue {

    String[] phrase() {
        ["zapal niebieskie"]
    }

    String act() {
        GpioController gpioController = GpioFactory.getInstance()
        GpioPinDigitalOutput pin0 = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00)
        pin0.high()
        pin0.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF)
    }
}