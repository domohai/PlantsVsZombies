package components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StateMachine extends Component {
	private class StateTrigger {
		public String state, trigger;
		public StateTrigger() {}
		public StateTrigger(String state, String trigger) {
			this.state = state;
			this.trigger = trigger;
		}
		@Override
		public boolean equals(Object o) {
			if (o.getClass() != StateMachine.class) {
				return false;
			}
			StateTrigger t = (StateTrigger)o;
			return t.trigger.equals(this.trigger) && t.state.equals(this.state);
		}
		@Override
		public int hashCode() {
			return Objects.hash(this.trigger, this.state);
		}
	}
	
	public HashMap<StateTrigger, String> stateTransfers = new HashMap<>();
	public List<AnimationState> states = new ArrayList<>();
	public transient AnimationState currentState = null;
	public String defaultStateTitle = "";
	
	public void addState(AnimationState state) {
		this.states.add(state);
	}
	
	public void setDefaultState(String animationTitle) {
		for (AnimationState a : this.states) {
			if (a.title.equals(animationTitle)) {
				this.defaultStateTitle = animationTitle;
				if (this.currentState == null) {
					this.currentState = a;
					return;
				}
			}
		}
		System.out.println("Cannot find state: " + animationTitle + " in setDefaultState");
	}
	
	public void addStateTrigger(String from, String to, String onTrigger) {
		this.stateTransfers.put(new StateTrigger(from, onTrigger), to);
	}
	
	public void trigger(String trigger) {
		for (StateTrigger state : this.stateTransfers.keySet()) {
			if (state.state.equals(this.currentState.title) && state.trigger.equals(trigger)) {
				if (this.stateTransfers.get(state) != null) {
					int newStateIndex = stateIndexOf(this.stateTransfers.get(state));
					if (newStateIndex > -1) {
						this.currentState = this.states.get(newStateIndex);
					}
				}
				return;
			}
		}
		//System.out.println("Cannot find Trigger: " + trigger);
	}
	
	private int stateIndexOf(String stateTitle) {
		int index = 0;
		for (AnimationState state : states) {
			if (state.title.equals(stateTitle)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	@Override
	public void start() {
		for (AnimationState a : this.states) {
			if (a.title.equals(defaultStateTitle)) {
				currentState = a;
				break;
			}
		}
	}
	
	@Override
	public void update(double dt) {
		if (this.currentState != null) {
			this.currentState.update(dt);
			/*
			Sprite sprite = this.gameObject.getComponent(Sprite.class);
			if (sprite != null) {
				sprite = this.currentState.getCurrentSprite();
			}
			*/
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		if (this.currentState != null) {
			g2D.drawImage(this.currentState.getCurrentSprite().image,
					(int) this.gameObject.transform.position.x,
					(int) this.gameObject.transform.position.y,
					this.currentState.getCurrentSprite().width,
					this.currentState.getCurrentSprite().height, null);
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
