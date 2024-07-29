import { Component,OnInit } from '@angular/core';
import { GetListProjectService } from '../../../services/directorServices/get-list-project.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DeleteProjectService } from '../../../services/directorServices/delete-project.service';
import { DeleteTaskService } from '../../../services/directorServices/delete-task.service';
import { OverlayPanel } from 'primeng/overlaypanel';
import { ChangeDetectorRef } from '@angular/core';
import { GetEmplyeeListService } from '../../../services/directorServices/get-emplyee-list.service';
import { AffecterService } from '../../../services/directorServices/affecter.service';
import { DetailsTacheService } from '../../../services/directorServices/details-tache.service';
import { AutoAffectService } from '../../../services/directorServices/auto-affect.service';
import { Router } from '@angular/router';
import { TasksTermineService } from '../../../services/directorServices/tasks-termine.service';


interface TableRowSelectEvent {
  originalEvent?: Event;
  data?: any;
  type?: string;
  index?: number;
}

@Component({
  selector: 'app-list-projet',
  templateUrl: './list-projet.component.html',
  styleUrl: './list-projet.component.css'
})
export class ListProjetComponent implements OnInit{
  result: any;
  selectedRole!:string;
  autoOrdenonceur:boolean=false;
  projects: any[]=[];
  private intervalId: any;
  task:any={
    id: 0,
    nomTache: "",
    type: "",
    description: "",
    dateDebut: "",
    dateFin: "",
    etat: "",
    priorite: "",
    users: [
        {
            id: 0,
            email: "",
            password: "",
            societe: "",
            nom: "",
            prenom: "",
            roles: [
                {
                    id: 0,
                    name: ""
                }
            ]
        }
    ]
  };
  expandedRows = {};
  Users: any[] = [];
  selecteduser: any | undefined;
  visible: boolean = false;

  constructor(private getlistprojects:GetListProjectService,
    private confirmationService: ConfirmationService, 
    private messageService: MessageService,
    private deleteproject:DeleteProjectService,
    private deleteTask:DeleteTaskService,
    private cdr: ChangeDetectorRef,
    private getuserslistservice:GetEmplyeeListService,
    private affecter:AffecterService,
    private getTask:DetailsTacheService,
    private auto:AutoAffectService,
    private router: Router,
    private terminee:TasksTermineService
  ) {}

  ngOnInit() {
    this.getlistprojects.getListUsers().subscribe((res)=>{
      let stat:number;
      let etatstat:string;
      let ress:any;
      
     
      for(let i=0;i<res.length;i++){
        let container:any;
        let tachess:any[]=[];
        if(res[i].nbrTaches!=0){stat=(res[i].nbrTachesTerminees/res[i].nbrTaches)*100}else{stat=0}

        if(res[i].nbrTachesTerminees==0){
          etatstat="UNSTARTED";
        }else if(res[i].nbrTachesTerminees==res[i].nbrTaches){
          etatstat="FINESHED";
        }else{etatstat="IN PROGRESS"}

        for(let j=0;j<res[i].taches.length;j++){
          let etat:string='';
          switch (res[i].taches[j].etat) {
            case 'TERMINEE':
                etat='FINESHED';
                break;
            case 'EN_COURS':
                etat= 'IN PROGRESS';
                break;
            case 'EN_ATTENTE':
                etat= 'UNSTARTED';
                break;
            case'Not_Affected':etat= 'Not Affected';break;
            default:  etat='Validated';break;
        }
          
          let containerTache={
            id:res[i].taches[j].id,
            name:res[i].taches[j].nomTache,
            type:res[i].taches[j].type,
            dateFin:res[i].taches[j].dateFin,
            priorite:res[i].taches[j].priorite,
            status:etat, 
         };
          tachess.push(containerTache);

        }


        container={
        id:res[i].id,
        name:res[i].nomProjet,
        nbrTasks:res[i].nbrTaches,
        inventoryStatus:etatstat,
        value : [ { label: '', value: stat, color: '#34d399' } ],
        tasks:tachess
        }
        this.projects.push(container);
      }
    });

    this.getuserslistservice.getListEmployyes().subscribe((res)=>{this.Users=res.users;console.log(this.Users)});  

    const autoValue = localStorage.getItem('auto');
    if (autoValue !== null && autoValue === 'true') {
      this.autoOrdenonceur = true;
    } else {
      this.autoOrdenonceur = false;
    }
  }
   
  collapseAll() {
      this.expandedRows = {};
  }

  getSeverity(status: string):'success'|'warning'|'danger'|undefined {
      switch (status) {
          case 'FINESHED':
              return 'success';
          case 'IN PROGRESS':
              return 'warning';
          case 'UNSTARTED':
              return 'danger';
          case'Not Affected':return 'danger';
          default:  return 'success';
      }
  }
  

    
  roleOptions = [
    { label: 'FINESHED', value: 'FINESHED' },
    { label: 'IN PROGRESS', value: 'IN PROGRESS' },
    { label: 'UNSTARTED', value: 'UNSTARTED' }
  ];
  showDialog(idp:number,idt:number) {
    this.getTask.getTache(idp,idt).subscribe((res)=>{this.task=res;});
    this.visible = true;
  }


  confirm2(event: Event,id:any) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Do you want to delete this Project?',
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass: 'p-button-danger p-button-sm',
        accept: () => {
            this.deleteproject.deleteProject(id).subscribe((res)=>{
              this.projects = this.projects.filter(project => project.id !== id);
              this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'project deleted', life: 3000 });
            },(error) => {
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete project', life: 3000 });
          });
        },
        reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
        }
    });
  }
  confirm1(event: Event,idp:any,idt:any) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Do you want to delete this Task?',
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass: 'p-button-danger p-button-sm',
        accept: () => {
            this.deleteTask.deleteTask(idp,idt).subscribe((res)=>{
              for (let i = 0; i < this.projects.length; i++) {
                if (this.projects[i].id === idp) {
                  for (let j = 0; j < this.projects[i].tasks.length; j++) {
                    if (this.projects[i].tasks[j].id === idt) {
                      delete this.projects[i].tasks[j];
                      this.projects[i].nbrTasks--;
                      break;
                    }
                }
              }
            }
              this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Task deleted', life: 3000 });
            },(error) => {
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete Task', life: 3000 });
          });
        },
        reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
        }
    });
  }
  onRowSelect(event: TableRowSelectEvent, op: OverlayPanel,idp:number,idt:number) {
    this.affecter.affecter(this.selecteduser.id,idp,idt).subscribe((res)=>{
      this.messageService.add({ severity: 'info', summary: 'Task Affected successfully ', detail: event.data.name });
      for (let i = 0; i < this.projects.length; i++) {
        if (this.projects[i].id === idp) {
          for (let j = 0; j < this.projects[i].tasks.length; j++) {
            if (this.projects[i].tasks[j].id === idt) {
              this.projects[i].tasks[j].status = 'UNSTARTED';
              break;
            }
        }
      }}
      op.hide();
    },(error) => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to affect task', life: 3000 });
    });
    
}


  ordenancer(evnt:any){
    localStorage.setItem('auto',this.autoOrdenonceur.toString());
    if(this.autoOrdenonceur){
      this.intervalId=setInterval(() => {this.auto.affecter().subscribe((res)=>{this.messageService.add({ severity: 'info', summary: 'Auto Affect', detail: 'Auto Affect Done', life: 3000 });
      for (let i = 0; i < this.projects.length; i++) { 
        for (let j = 0; j < this.projects[i].tasks.length; j++) {
          if (this.projects[i].tasks[j].status == 'Not Affected') {
            this.projects[i].tasks[j].status = 'UNSTARTED';
          }
      }}
    });}, 300000);
    }else {
      clearInterval(this.intervalId);
      this.intervalId = null;
    }
  }
}
