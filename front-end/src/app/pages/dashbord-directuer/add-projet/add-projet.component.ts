import { Component,OnInit } from '@angular/core';
import { AddProjectService } from '../../../services/directorServices/add-project.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-projet',
  templateUrl: './add-projet.component.html',
  styleUrl: './add-projet.component.css'
})
export class AddProjetComponent implements OnInit{
  ProjectName: string='';  
  description: string='';
  constructor(private addProject:AddProjectService,private router:Router) {}

  ngOnInit(){}

  onsubmit(){
    this.addProject.addProject(this.ProjectName,this.description).subscribe(
      data=>{
        alert('Project added successfully');
        this.router.navigate(['/dashbordDirector/listProjet']);
      },
      error=>{
        alert('error');
      }
    );
  }
}
