package hardcorequesting.commands;

import hardcorequesting.QuestingData;
import hardcorequesting.Team;
import hardcorequesting.TeamStats;
import hardcorequesting.network.DataBitHelper;
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
			String playertoadd = arguments[2];
			if (!QuestingData.hasData(playertoadd))
			{
				sendChat(sender, Team.ErrorMessage.INVALID_PLAYER.getHeader());
				sendChat(sender, Team.ErrorMessage.INVALID_PLAYER.getMessage());
				return;
			}

			if (!QuestingData.getQuestingData(playertoadd).getTeam().isSingle())
			{
				sendChat(sender, Team.ErrorMessage.IN_PARTY.getHeader());
				sendChat(sender, Team.ErrorMessage.IN_PARTY.getMessage());
				return;
			}
			List<Team> teamList = QuestingData.getTeams();
			Team team = teamList.get(Integer.getInteger(arguments[1]));
			if(team==null)
			{
				//sendChat(sender,"");
				return;
			}
			EntityPlayer entityPlayertoadd = QuestingData.getPlayer(playertoadd);
			Team.PlayerEntry entry = new Team.PlayerEntry(playertoadd, true, false);
			team.getPlayers().add(entry);
			QuestingData.getQuestingData(entityPlayertoadd).setTeam(team);


			team.refreshData();
			team.refreshTeamData(Team.UpdateType.ALL);
			//Team.declineAll(playertoadd);
			TeamStats.refreshTeam(team);

		}
		else if (arguments[0]=="remove")
		{
			List<Team> teamList = QuestingData.getTeams();
			Team team = teamList.get(Integer.getInteger(arguments[1]));
			if(QuestingData.getQuestingData(arguments[2])==null||team==null||team.getEntry(arguments[2])==null)
			{
				sendChat(sender, Team.ErrorMessage.INVALID_PLAYER.getHeader());
				sendChat(sender, Team.ErrorMessage.INVALID_PLAYER.getMessage());
				return;
			}

			team.removePlayer(arguments[2]);
		}
	}
}
