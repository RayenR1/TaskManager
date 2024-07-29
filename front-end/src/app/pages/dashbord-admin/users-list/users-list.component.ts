import { Component ,OnInit,ViewChild} from '@angular/core';
import { GetListUsersService } from '../../../services/adminServices/get-list-users.service';
import { Table } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { CommonModule } from '@angular/common';

interface role {
  label: string;
  value: string|null;
}


@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent implements OnInit{
  @ViewChild('dt2') dt2!: Table;
  listUsres:any[]=[];
  aleur!:boolean;
  selectedRole: string | null = null; 
  roleOptions: role[]| undefined;
  statuses!: any[];
  expandedRows={};
  roles: string = '';
  loading: boolean = false;
  

  activityValues: number[] = [0, 100];
 
  
  constructor(private getuserslistservice:GetListUsersService){}
  ngOnInit(){
    const validRoles = ['admin', 'director', 'employee'];
    this.getuserslistservice.getListUsers().subscribe((data:any)=>{
      
      for (let i = 0; i < data.users.length; i++) {
        for(let j=0;j<data.users[i].roles.length;j++){
          if (validRoles.includes(data.users[i].roles[j].name)){
          this.roles+=' '+data.users[i].roles[j].name+' ' + '|';}
        }
        let userModel = {
          id: data.users[i].id,
          nom: data.users[i].nom,
          prenom: data.users[i].prenom,
          online: data.users[i].online,
          roles: this.roles,
        };
        this.listUsres.push(userModel);
        this.roles = '';
        }
        this.roleOptions = [
          { label: 'No Filter', value: null },
          { label: 'Admin', value: 'admin' },
          { label: 'Director', value: 'director' },
          { label: 'Employee', value: 'employee' }
        ];
        this.statuses = [
            { label: 'OFFLine', value: 'OFFLine' },
            { label: 'OnLine', value: 'OnLine' },
            
        ];

        
    });
    
  }
  get estVide(): boolean {
    return this.listUsres.length === 0;
  }
  clear(table: Table) {
    table.clear();
}

getSeverity(status: boolean): 'success'  | 'danger' | 'secondary' | undefined { 
  switch (status) {
      case true:
          return 'success';
      case false:
          return 'danger';
      default:
          return 'secondary'; 
  }
}
applyGlobalFilter(event: any) {
  this.dt2?.filterGlobal((event.target as HTMLInputElement).value, 'contains'); 
}
etat(etat:boolean):string{
  if(etat==true){
 return 'OnLine'}else{return 'OFFLine'}
}

  

}
