package factory.controller;

import java.util.concurrent.Semaphore;
import factory.model.DigitalSignal;
import factory.model.WidgetKind;
import factory.simulation.Painter;
import factory.simulation.Press;
import factory.swingview.Factory;

/**
 * Implementation of the ToolController interface, to be used for the Widget
 * Factory lab.
 * 
 * @see ToolController
 */
public class LabToolController implements ToolController {
	private final DigitalSignal conveyor, press, paint;
	private final long pressingMillis, paintingMillis;
	private boolean paint_on;
	private boolean press_on;

	public LabToolController(DigitalSignal conveyor, DigitalSignal press, DigitalSignal paint, long pressingMillis,
			long paintingMillis) {
		this.conveyor = conveyor;
		this.press = press;
		this.paint = paint;
		this.pressingMillis = pressingMillis;
		this.paintingMillis = paintingMillis;
		this.paint_on = false;
		this.press_on = false;
	}

	@Override
	public synchronized void onPressSensorHigh(WidgetKind widgetKind) throws InterruptedException {
		//
		// TODO: you will need to modify this method.
		//
		// Note that this method can be called concurrently with onPaintSensorHigh
		// (that is, in a separate thread).
		//
		if (widgetKind == WidgetKind.GREEN_RECTANGULAR_WIDGET) {
			//mutextime.acquire();
			con_off_press();
			press.on();
			waitOutside(pressingMillis);
			press.off();
			waitOutside(pressingMillis);
			con_on_press();
			//mutextime.release();
		}
	}

	@Override
	public synchronized void onPaintSensorHigh(WidgetKind widgetKind) throws InterruptedException {
		//
		// TODO: you will need to modify this method.
		//
		// Note that this method can be called concurrently with onPressSensorHigh
		// (that is, in a separate thread).
		//
		if (widgetKind == WidgetKind.ORANGE_ROUND_WIDGET) {
			//mutextime.acquire();
			con_off_paint();
			paint.on();
			waitOutside(paintingMillis);
			paint.off();
			con_on_paint();
			//mutextime.release();

		}
	}
	private synchronized void con_on_press() throws InterruptedException {
		press_on = false;
		while (paint_on) {
			wait();
		}
		
		conveyor.on();
		notifyAll();
	}
	private synchronized void con_on_paint() throws InterruptedException {
		paint_on = false;
		while (press_on) {
			wait();
		}
		
		conveyor.on();
		notifyAll();
	}
	private synchronized void con_off_paint() {
		paint_on = true;
		conveyor.off();
		
	}
	private synchronized void con_off_press() {
		press_on = true;
		conveyor.off();
	}
	/** Helper method: wait outside of monitor for 'millis' milliseconds. */
	private void waitOutside(long millis) throws InterruptedException {
		long timeToWakeUp = System.currentTimeMillis() + millis;
		// ...
		while (timeToWakeUp > System.currentTimeMillis() ) {
			long dt = timeToWakeUp - System.currentTimeMillis();
			wait(dt);
			// ...
		}
	}
	// -----------------------------------------------------------------------

	public static void main(String[] args) {
		Factory factory = new Factory();
		ToolController toolController = new LabToolController(factory.getConveyor(), factory.getPress(),
				factory.getPaint(), Press.PRESSING_MILLIS, Painter.PAINTING_MILLIS);
		factory.startSimulation(toolController);
	}
}
