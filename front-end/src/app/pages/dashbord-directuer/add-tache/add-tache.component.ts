import { Component,OnInit } from '@angular/core';
import { AddTacheService } from '../../../services/directorServices/add-tache.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-add-tache',
  templateUrl: './add-tache.component.html',
  styleUrl: './add-tache.component.css'
})
export class AddTacheComponent implements OnInit{
  intervalId:any;
  tache:any={   
    nomTache:'',
    type:'',
    description:'',
    dateDebut:'',
    dateFin:'',
    etat:"",
    priorite:'', 
  }  
  priorites = [
    { name: 'High ', value: 'HAUTE' },
    { name: 'Medium ', value: 'MOYENNE' },
    { name: 'Low ', value: 'BASSE' }
];
  constructor(private addTache:AddTacheService, private route:ActivatedRoute,private messageService: MessageService,private router:Router) { }
  ngOnInit(): void {
      
  }
  onsubmit(){ 
    this.addTache.addTache(this.route.snapshot.params["id"],this.tache).subscribe( (response) => {
      this.messageService.add({
        severity: 'info',
        summary: 'Confirmed',
        detail: 'Task added successfully',
        life: 3000
      })
      this.tache={   
        nomTache:'',
        type:'',
        description:'',
        dateDebut:'',
        dateFin:'',
        etat:"",
        priorite:'', 
      }
     
    },
    (error) => {
      console.error(error);
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Failed to add task',
        life: 2000
      });});
    
  }

}
