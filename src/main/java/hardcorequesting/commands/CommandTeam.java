package hardcorequesting.commands;

import hardcorequesting.QuestingData;
import hardcorequesting.Team;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class CommandTeam extends CommandBase
{

	public CommandTeam()
	{
		super("team");
	}

	@Override
	public void handleCommand(ICommandSender sender, String[] arguments)
	{
		if(arguments.length!=3)
		{
			sendChat(sender, "hqm.command.team.syntax0");
		}


		if(arguments[0]=="add")
		{
			if(QuestingData.getQuestingData(arguments[2])==null||QuestingData.getQuestingData(arguments[2]).getTeam()!=null)
			{
				return;
			}
			List<Team> teamList = QuestingData.getTeams();
			Team team = teamList.get(Integer.getInteger(arguments[1]));
			if(team==null){return;}


		}
		else if (arguments[0]=="remove")
		{
			List<Team> teamList = QuestingData.getTeams();
			Team team = teamList.get(Integer.getInteger(arguments[1]));
			if(QuestingData.getQuestingData(arguments[2])==null||team==null||team.getEntry(arguments[2])==null)
			{
				return;
			}

			team.removePlayer(arguments[2]);
		}
	}
}
