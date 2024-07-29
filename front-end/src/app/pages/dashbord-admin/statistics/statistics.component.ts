import { Component ,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { StatNbrUsresService } from '../../../services/adminServices/stat-nbr-usres.service';
import { StatNbrOnlineService } from '../../../services/adminServices/stat-nbr-online.service';
import { StatNbrEquipesService } from '../../../services/adminServices/stat-nbr-equipes.service';
import { StatNbrRolesService } from '../../../services/adminServices/stat-nbr-roles.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrl: './statistics.component.css'
})
export class StatisticsComponent implements OnInit{
  nbrUsers = 1;
  nbrAdmins = 1;
  nbrDirectors = 0;
  nbrEmployees = 0;
  nbrTeams = 0;
  nbrOnline =1;
  intervalId: any;
  societe!:string
  value: any;
  onlineporcentage = Math.round((this.nbrOnline*100)/this.nbrUsers);
  offlineporcentage = Math.round(100 - this.onlineporcentage);
  
  updatePercentages() {
    this.onlineporcentage = Math.round((this.nbrOnline * 100) / this.nbrUsers);
    this.offlineporcentage = Math.round(100 - this.onlineporcentage);
    this.value = [
      { label: 'online user', value: this.onlineporcentage, color: '#34d399' },
      { label: 'offline user', value: this.offlineporcentage, color: '#da2424' }
    ];
  }

  ngOnInit(): void {
    console.log('refreshed');
    this.StatnbrUsers.statNbrUsers().subscribe(
      (data:any)=>{
        this.nbrUsers = data.result;
              },
      error=>{
        console.log(error);
      }
    );

    this.serviceonline.nbrOnline().subscribe(
    (data:any)=>{
      this.nbrOnline = data.result;
    },
    error=>{
      console.log(error);
    });

    this.statteamservice.nbrEquipes().subscribe(
      (data:any)=>{
        this.nbrTeams = data.result;
      },
      error=>{
        console.log(error);
      });

      this.statByrole.nbrbyRole("admin").subscribe(
        (data:any)=>{
          this.nbrAdmins = data.result;
        },
        error=>{
          console.log(error);
        });

      this.statByrole.nbrbyRole("director").subscribe(
        (data:any)=>{
          this.nbrDirectors = data.result;
        },
        error=>{
          console.log(error);
        });
      this.statByrole.nbrbyRole("employee").subscribe(
        (data:any)=>{
          this.nbrEmployees = data.result;
        },
        error=>{
          console.log(error);
        });
        this.onlineporcentage = (this.nbrOnline*100)/this.nbrUsers;
        this.offlineporcentage = 100 - this.onlineporcentage;
        this.updatePercentages();

        
    this.intervalId = setInterval(() => {
      console.log('refreshed');
    this.StatnbrUsers.statNbrUsers().subscribe(
      (data:any)=>{
        this.nbrUsers = data.result;
              },
      error=>{
        console.log(error);
      }
    );

    this.serviceonline.nbrOnline().subscribe(
    (data:any)=>{
      this.nbrOnline = data.result;
    },
    error=>{
      console.log(error);
    });

    this.statteamservice.nbrEquipes().subscribe(
      (data:any)=>{
        this.nbrTeams = data.result;
      },
      error=>{
        console.log(error);
      });

      this.statByrole.nbrbyRole("admin").subscribe(
        (data:any)=>{
          this.nbrAdmins = data.result;
        },
        error=>{
          console.log(error);
        });

      this.statByrole.nbrbyRole("director").subscribe(
        (data:any)=>{
          this.nbrDirectors = data.result;
        },
        error=>{
          console.log(error);
        });
      this.statByrole.nbrbyRole("employee").subscribe(
        (data:any)=>{
          this.nbrEmployees = data.result;
        },
        error=>{
          console.log(error);
        });
        this.onlineporcentage = (this.nbrOnline*100)/this.nbrUsers;
        this.offlineporcentage = 100 - this.onlineporcentage;
        this.updatePercentages();

    }, 3000);  
    
  }
  constructor(private router:Router,private StatnbrUsers:StatNbrUsresService,private serviceonline:StatNbrOnlineService,private statteamservice:StatNbrEquipesService,private statByrole:StatNbrRolesService) {}
  
}
