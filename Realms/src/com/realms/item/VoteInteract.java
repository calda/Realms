package com.realms.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.realms.command.Vote;

public class VoteInteract extends ItemInteraction{

	private final String vote;
	
	public VoteInteract(String v) {
		super(true, true, false);
		this.vote = v;
	}

	@Override
	public void run(Player interact, Entity on, InteractionType interaction) {
		if(interaction != InteractionType.LEFT && interaction != InteractionType.RIGHT) return;
		String[] vote = new String[1];
		vote[0] = this.vote;
		Vote.instance.execute(interact, vote);
	}

}
