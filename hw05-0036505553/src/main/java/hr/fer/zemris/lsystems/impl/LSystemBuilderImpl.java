package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;
/**
 * This class implements {@link LSystemBuilderImpl}. It is used to build {@link LSystem}
 * Every builder has five parameters unitLength,unitLengthDegreeScaler,origin,angle,axiom.
 * Also it contains two {@link Dictionary} for saving commands generated from the productions from 
 * the axiom. It can be created from the text or by using register commands.
 * @author Patrik Okanovic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Saves the commands for symbols
	 */
	private Dictionary<Character, Command> commands = new Dictionary<>();
	/**
	 * Saves grammar productions for the symbol.
	 */
	private Dictionary<Character, String> production = new Dictionary<>();
	
	/**
	 * The unitLength
	 */
	private double unitLength = 0.1;
	/**
	 * The unitLengthDegreeScaler
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * The origin
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * The angle
	 */
	private double angle = 0;
	/**
	 * The axiom
	 */
	private String axiom = "";

	

	/**
	 * Creates and initializes the {@link LSystemBuilderImpl} from the text.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(String data : arg0) {
			String[] dataElements;
			dataElements = data.split("\\s+");
			if(data.isBlank()) {
				continue;
			} else if(data.startsWith("origin")) {
				try {
					double x = Double.parseDouble(dataElements[1]);
					double y = Double.parseDouble(dataElements[2]);
					this.origin = new Vector2D(x,y);
				} catch(NumberFormatException exc) {
					throw new IllegalArgumentException("Cannot parse origin."); 
				}
				
			} else if(data.startsWith("angle")) {
				try {
					this.angle = Double.parseDouble(dataElements[1]) * Math.PI / 180;
				} catch(NumberFormatException exc) {
					throw new IllegalArgumentException("Cannot parse angle."); 
				}
				
				
			} else if(data.startsWith("unitLengthDegreeScaler")) {
				String s = "";
				for(int i = 1; i < dataElements.length; i++) {
					s += dataElements[i];
				}
				if(s.contains("/")) {
					String[] numbers = s.split("/");
					try {
						this.unitLengthDegreeScaler = Double.parseDouble(numbers[0]) /
												Double.parseDouble(numbers[1])	;
					} catch(NumberFormatException exc) {
						throw new IllegalArgumentException("Cannot parse unitLengthDegreeScaler");
					}
					
				} else {
					try {
						this.unitLengthDegreeScaler = Double.parseDouble(s);
					} catch(NumberFormatException exc) {
						throw new IllegalArgumentException("Cannot parse unitLengthDegreeScaler");
					}
				}
				
			} else if(data.startsWith("unitLength")) {
				try {
					this.unitLength = Double.parseDouble(dataElements[1]);
				} catch(NumberFormatException exc) {
					throw new IllegalArgumentException("Cannot parse unitLength."); 
				}
				
			} else if(data.startsWith("command")) {
				Character key = null;
				if(dataElements[1].length() == 1) {
					key = dataElements[1].charAt(0);
				} else {
					throw new IllegalArgumentException("Illegal command");
				}
				
				StringBuilder sb = new StringBuilder();
				for(int i = 2; i < dataElements.length; i++) {
					sb.append(dataElements[i]);
					if(i + 1 != dataElements.length) {
						sb.append(" ");
					}
				}
				
				registerCommand(key, sb.toString());
				
			} else if(data.startsWith("axiom")) {
				
				this.axiom = dataElements[1];
				
			} else if(data.startsWith("production")) {
				
				Character key = null;
				if(dataElements[1].length() == 1) {
					key = dataElements[1].charAt(0);
				} else {
					throw new IllegalArgumentException("Illegal production.");
				}
				
				production.put(key, dataElements[2]);
				
				
			} else {
				throw new IllegalArgumentException("Illegal data.");
			}
		}
		return this;
	}

	/**
	 * Used to register the {@link Command} for a symbol.
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] dataElements = arg1.split("\\s+");
		Character key = arg0;
		
		if(arg1.contains("draw")) {
			try {
				double step = Double.parseDouble(dataElements[1]);
				commands.put(key, new DrawCommand(step));
			} catch(NumberFormatException exc) {
				throw new IllegalArgumentException("Cannot register drawCommand");
			}
			
		} else if(arg1.contains("skip")) {
			try {
				double step = Double.parseDouble(dataElements[1]);
				commands.put(key, new SkipCommand(step));
				
			} catch(NumberFormatException exc) {
				throw new IllegalArgumentException("Cannot register skipCommand");
			}
			
		} else if(arg1.contains("scale")) {
			try {
				double scale = Double.parseDouble(dataElements[1]);
				commands.put(key, new ScaleCommand(scale));
				
			} catch(NumberFormatException exc) {
				throw new IllegalArgumentException("Cannot register scaleCommand");
			}
			
		} else if(arg1.contains("rotate")) {
			try {
				double angle = Double.parseDouble(dataElements[1]);
				commands.put(key, new RotateCommand(angle * Math.PI / 180));
				
			} catch(NumberFormatException exc) {
				throw new IllegalArgumentException("Cannot register rotateCommand");
			}
			
		} else if(arg1.contains("push")) {
			commands.put(key, new PushCommand());
			
		} else if(arg1.contains("pop")) {
			commands.put(key, new PopCommand());
			
		} else if(arg1.contains("color")) {
			Color color = Color.decode("#" + dataElements[1]);
			commands.put(key, new ColorCommand(color));
			
		} else {
			throw new IllegalArgumentException("Non existing command! ");
		}
		return this;
	}

	/**
	 * Registers the production for the symbol.
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		production.put(arg0, arg1);
		return this;
	}

	/**
	 * Sets the angle
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0 * Math.PI / 180;
		return this;
	}

	/**
	 * Sets the axiom.
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	/**
	 * Sets the origin
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Sets the unitLength
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	/**
	 * Sets the UnitLengthDegreeScaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
	/**
	 * Returns an implementation of {@link LSystem}
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl(this);
	}
	
	/**
	 * Used to implement {@link LSystem}
	 * @author Patrik Okanovic
	 *
	 */
	private static class LSystemImpl implements LSystem {
		
		private LSystemBuilderImpl impl;
		public LSystemImpl(LSystemBuilderImpl impl) {
			this.impl = impl;
		}
		/**
		 * Draws on the window based on the commands. Creates an {@link TurtleState}
		 * pointed to the right in the (0,0) with colour black.
		 */
		@Override
		public void draw(int arg0, Painter painter) {
			Context context = new Context();
			
			TurtleState state = new TurtleState(impl.origin, new Vector2D(1, 0).rotated(impl.angle) 
					,Color.BLACK, impl.unitLength* Math.pow(impl.unitLengthDegreeScaler, arg0));
			
			context.pushState(state);
			
			String generated = generate(arg0);
			
			for(char c : generated.toCharArray()) {
				try {
					Command command = impl.commands.get(c);
					if(command != null) {
						command.execute(context, painter);
					}
				} catch(NoSuchElementException exc) {
					//ignore, necessary for fractals from primjeri.zip
				}
				
			}
			
		}

		/**
		 * Generates a string from the axiom based on the depth and using grammar productions.
		 */
		@Override
		public String generate(int arg0) {
			
			String generation = impl.axiom;
			
			for(int i = 0; i < arg0; i++) {
				StringBuilder sb = new StringBuilder();
				for(char c : generation.toCharArray()) {
					try {
						String s = impl.production.get(c);
						sb.append(s);
					} catch(NoSuchElementException exc) {
						sb.append(c);
					}
				}
				generation = sb.toString();
			}
			return generation;
		}
	}
	

}
