import { Component,OnInit } from '@angular/core';
import { ToValidateService } from '../../../services/directorServices/to-validate.service';
import { ValiderService } from '../../../services/directorServices/valider.service';
import { MessageService } from 'primeng/api';
import { error } from 'console';
import { PerformenceService } from '../../../services/directorServices/performence.service';  

@Component({
  selector: 'app-stat',
  templateUrl: './stat.component.html',
  styleUrl: './stat.component.css'
})
export class StatComponent implements OnInit {
  tasks!: any[];
  performence!: any[];
  constructor(private tovalidate: ToValidateService,private valide:ValiderService, private messageService: MessageService,private performenceService:PerformenceService) {}

  ngOnInit(): void {
    this.tovalidate.getTasks().subscribe((data: any[]) => {
      this.tasks = data;
    });

    this.performenceService.getPerformance().subscribe((data: any[]) => {
      this.performence = data;
      this.sortTasks('validatedTasksCount', 'desc');
    });

  }
  sortTasks(field: string, order: 'asc' | 'desc' = 'asc') {
    this.performence.sort((a, b) => {
      let comparison = 0;

      if (a[field] < b[field]) {
        comparison = -1;
      } else if (a[field] > b[field]) {
        comparison = 1;
      }

      return order === 'desc' ? comparison * -1 : comparison;
    });
  }

  valider(idt:any) {this.valide.response(idt,'Valide').subscribe((data)=>{this.tasks = this.tasks.filter((val) => val.id !== idt);this.performenceService.getPerformance().subscribe((data: any[]) => {
    this.performence = data;
    this.sortTasks('validatedTasksCount', 'desc');
  });
  },(error)=>{this.tasks = this.tasks.filter((val) => val.id !== idt);this.performenceService.getPerformance().subscribe((data: any[]) => {
    this.performence = data;
    this.sortTasks('validatedTasksCount', 'desc');
  });}); 
}

  refuser(idt:any) {this.valide.response(idt,'Not_Valide').subscribe((data) => {
    this.tasks=this.tasks.filter((val) => val.id !== idt);this.performenceService.getPerformance().subscribe((data: any[]) => {
      this.performence = data;
      this.sortTasks('validatedTasksCount', 'desc');
    });
  },(error)=>{this.tasks = this.tasks.filter((val) => val.id !== idt);});this.performenceService.getPerformance().subscribe((data: any[]) => {
    this.performence = data;
    this.sortTasks('validatedTasksCount', 'desc');
  }); }

  
}
