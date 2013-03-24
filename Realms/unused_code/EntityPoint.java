package com.realms.mode.map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import com.realms.general.Team;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;

public class EntityPoint extends OwnedPoint{
	
	private final Entity point;
	
	public EntityPoint(Entity point, int radius, Team defaultOwner, int ticksToCap, int capTimeToWin, Team... canCap){
		super(null, radius, defaultOwner, ticksToCap, capTimeToWin, canCap);
		this.point = point;
		final EntityPoint thiz = this;
		SchedulerManager.registerRepeatingTicks(RealmsMain.getGameTypeName(), "entityPoint", 0, 1, new Runnable(){
			public void run(){
				thiz.updateVelocity();
			}
		});
	}
	
	@Override
	public Location getPoint(){
		return point.getLocation();
	}
	
	public Entity getEntity(){
		return point;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getEntityAs(Class<T> clazz){
		if(clazz.isInstance(point)) return (T) point;
		else throw new IllegalArgumentException("The point object is a " + point.getClass().getName() + ", not a " + clazz.getName());
	}
	
	@Override
	public void updateBlocks(){ }
	
	private void updateVelocity(){
		
	}

}
