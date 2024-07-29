import { Component,ChangeDetectorRef,EventEmitter, Output  } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { RoleGuard } from '../../services/role-gard.service';
import { GetEmplyeeTaskService } from '../../services/employeeServices/get-emplyee-task.service';
import { UpdateEtatTacheService } from '../../services/employeeServices/update-etat-tache.service';
import { MessageService } from 'primeng/api';
import { error } from 'console';
import { HistoriqueService } from '../../services/employeeServices/historique.service';

@Component({
  selector: 'app-dashbord-employee',
  templateUrl: './dashbord-employee.component.html',
  styleUrl: './dashbord-employee.component.css'
})
export class DashbordEmployeeComponent {
  data: any;
  options: any;
  active: number | undefined = 0;

  encours: number  = 0;
  termine: number  = 0;
  enattente: number= 0;
  historique:number = 0;
  etats: string[] = ['EN_ATTENTE', 'EN_COURS', 'TERMINEE'];
  
  sourceTasks: any[]=[];
  targetTasks: any[]=[];

  constructor(private loginser:LoginService,private rolegard:RoleGuard,private cdr: ChangeDetectorRef,private listTaches:GetEmplyeeTaskService,private updateetat:UpdateEtatTacheService,private messageService:MessageService,
    private historiqueService:HistoriqueService
  ){}
  ngOnInit(){this.rolegard.role = 'employee';
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    this.historiqueService.getHistorique().subscribe((data)=>{this.historique=data.length},(error)=>{});

    
    this.listTaches.getListTask().subscribe((data)=>{
      for (let i = 0; i < data.length; i++) {
        if(data[i].etat == 'EN_ATTENTE'){
          const taskcontainer = { ...data[i], active: 0 }; 
          this.sourceTasks.push(taskcontainer);
          this.enattente++;
        }else if(data[i].etat == 'EN_COURS'){
          const taskcontainer = { ...data[i], active: 1};
          this.targetTasks.push(taskcontainer);
          this.encours++;
        }else if(data[i].etat == 'TERMINEE'){
          const taskcontainer = { ...data[i], active: 2 };
          this.targetTasks.push(taskcontainer);
          this.termine++;
        }
      };
      this.data = {
        labels: ['A', 'B', 'C'],
        datasets: [
            {
                data: [this.enattente, this.encours, this.termine],
                backgroundColor: [documentStyle.getPropertyValue('--red-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--green-500')],
                hoverBackgroundColor: [documentStyle.getPropertyValue('--red-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
            }
        ]
    };
    this.options = {
        cutout: '60%',
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        }
    };

    });
  }

  loadTasks() {
    this.sourceTasks = [];
    this.targetTasks = [];
    this.enattente = 0;
    this.encours = 0;
    this.termine = 0;
    const documentStyle = getComputedStyle(document.documentElement);

    this.listTaches.getListTask().subscribe((data) => {
      for (let i = 0; i < data.length; i++) {
        if (data[i].etat === 'EN_ATTENTE') {
          const taskcontainer = { ...data[i], active: 0 };
          this.sourceTasks.push(taskcontainer);
          this.enattente++;
        } else if (data[i].etat === 'EN_COURS') {
          const taskcontainer = { ...data[i], active: 1 };
          this.targetTasks.push(taskcontainer);
          this.encours++;
        } else if (data[i].etat === 'TERMINEE') {
          const taskcontainer = { ...data[i], active: 2 };
          this.targetTasks.push(taskcontainer);
          this.termine++;
        }
      }
      this.data = {
        labels: ['A', 'B', 'C'],
        datasets: [
            {
                data: [this.enattente, this.encours, this.termine],
                backgroundColor: [documentStyle.getPropertyValue('--red-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--green-500')],
                hoverBackgroundColor: [documentStyle.getPropertyValue('--red-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
            }
        ]
    };    });
  }

  onPrev(task:any) {
    const newEtat = this.etats[task.active - 1];
    this.updateetat.updateEtatTache(task.id, newEtat).subscribe((data)=>{
      this.loadTasks()
    },(error)=>{ this.loadTasks()});
  }

  onNext(task:any) {
    const newEtat = this.etats[task.active + 1];
    this.updateetat.updateEtatTache(task.id, newEtat).subscribe((data)=>{
       this.loadTasks() 
    },(error)=>{ this.loadTasks()});
  }



}
