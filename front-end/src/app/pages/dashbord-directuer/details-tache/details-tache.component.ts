import { Component,OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UpdateTaskService } from '../../../services/directorServices/update-task.service';
import { DetailsTacheService } from '../../../services/directorServices/details-tache.service';

@Component({
  selector: 'app-details-tache',
  templateUrl: './details-tache.component.html',
  styleUrl: './details-tache.component.css'
})
export class DetailsTacheComponent implements OnInit {
  intervalId:any;
  tache:any;  
  priorites = [
    { name: 'High ', value: 'HAUTE' },
    { name: 'Medium ', value: 'MOYENNE' },
    { name: 'Low ', value: 'BASSE' }
];
  constructor(private updateTache:UpdateTaskService, private route:ActivatedRoute,private messageService: MessageService,private router:Router,
    private getTask:DetailsTacheService
  ) { }
  ngOnInit(): void {
    this.getTask.getTache(this.route.snapshot.params["idp"],this.route.snapshot.params["idt"]).subscribe((response) => {
      this.tache=response;
    },);
      
  }
  onsubmit(){ 
    this.updateTache.updateTache(this.route.snapshot.params["idp"],this.route.snapshot.params["idt"],this.tache).subscribe( (response) => {
      this.messageService.add({
        severity: 'info',
        summary: 'Confirmed',
        detail: 'Task updated successfully',
        life: 3000
      })
      this.router.navigate(['/dashbordDirector/listProjet']); 
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
