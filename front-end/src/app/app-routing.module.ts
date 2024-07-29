import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { LayOutComponent } from './pages/lay-out/lay-out.component';
import { DashbordAdminComponent } from './pages/dashbord-admin/dashbord-admin.component';
import { AuthGuard } from '../app/services/auth.guard';
import { HomeComponent } from './pages/home/home.component';
import { DashbordDirectuerComponent } from './pages/dashbord-directuer/dashbord-directuer.component';
import { DashbordEmployeeComponent } from './pages/dashbord-employee/dashbord-employee.component';
import { RoleGuard } from './services/role-gard.service';
import { StatisticsComponent } from './pages/dashbord-admin/statistics/statistics.component';
import { AddUserComponent } from './pages/dashbord-admin/add-user/add-user.component';
import { UsersListComponent } from './pages/dashbord-admin/users-list/users-list.component';
import { DeleteUserComponent } from './pages/dashbord-admin/delete-user/delete-user.component';
import { DetailsUserComponent } from './pages/dashbord-admin/details-user/details-user.component';
import { EditUserComponent } from './pages/dashbord-admin/edit-user/edit-user.component';
import { ListProjetComponent } from './pages/dashbord-directuer/list-projet/list-projet.component';
import { AddProjetComponent } from './pages/dashbord-directuer/add-projet/add-projet.component';
import { AddTacheComponent } from './pages/dashbord-directuer/add-tache/add-tache.component';
import { StatComponent } from './pages/dashbord-directuer/stat/stat.component';
import { DetailsProjectComponent } from './pages/dashbord-directuer/details-project/details-project.component';
import { DetailsTacheComponent } from './pages/dashbord-directuer/details-tache/details-tache.component';


const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'',redirectTo:'login',pathMatch:'full'},
  {path:'',
    component:LayOutComponent,children:[{
      path:'dashbordAdmin',
      component:DashbordAdminComponent,
      canActivate:[RoleGuard],children:[{
        path:'statistics',
        component:StatisticsComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'adduser',
        component:AddUserComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'listusers',
        component:UsersListComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'deleteuser/:id',
        component:DeleteUserComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'edituser/:id',
        component:EditUserComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'detailsuser/:id',
        component:DetailsUserComponent,
        canActivate:[RoleGuard]
  
      }]

    },
    {
      path:'home',
      component:HomeComponent,
      canActivate:[AuthGuard]

    },{
      path:'dashbordDirector',
      component:DashbordDirectuerComponent,
      canActivate:[RoleGuard ],children:[{
        path:'listProjet',
        component:ListProjetComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'addprojet',
        component:AddProjetComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'addtache/:id',
        component:AddTacheComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'stat',
        component:StatComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'projectDetails/:id',
        component:DetailsProjectComponent,
        canActivate:[RoleGuard]
  
      },{
        path:'taskDetails/:idp/:idt',
        component:DetailsTacheComponent,
        canActivate:[RoleGuard]
  
      }]

    },{
      path:'dashboedEmploye',
      component:DashbordEmployeeComponent,
      canActivate:[RoleGuard ]

    }],canActivate:[AuthGuard]
  },
  {
    path:'**',
    component:LoginComponent
  }
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
