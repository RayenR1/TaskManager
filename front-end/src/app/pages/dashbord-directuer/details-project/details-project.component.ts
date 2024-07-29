import { Component,OnInit } from '@angular/core';
import { GetProjectByidService } from '../../../services/directorServices/get-project-byid.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-details-project',
  templateUrl: './details-project.component.html',
  styleUrl: './details-project.component.css'
})
export class DetailsProjectComponent implements OnInit{
  nomProjet:string='Erreur';
  description:string='Erreur';
  nbrTaches:number=-1;
  constructor(private getProjectById:GetProjectByidService, private route:ActivatedRoute) { }
  ngOnInit(): void {
      this.getProjectById.getProject(this.route.snapshot.params["id"]).subscribe(
        (data)=>{
          this.nomProjet=data.nomProjet;
          this.description=data.description;
          this.nbrTaches=data.nbrTaches;
      } , 
      (error)=>{  
        console.log(error);
      }); 
  }

}
