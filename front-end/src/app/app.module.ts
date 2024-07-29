import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { LayOutComponent } from './pages/lay-out/lay-out.component';
import { DashbordAdminComponent } from './pages/dashbord-admin/dashbord-admin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginInterceptor } from './services/interceptor.service';
import { HomeComponent } from './pages/home/home.component';
import { DashbordDirectuerComponent } from './pages/dashbord-directuer/dashbord-directuer.component';
import { DashbordEmployeeComponent } from './pages/dashbord-employee/dashbord-employee.component';
import { StatisticsComponent } from './pages/dashbord-admin/statistics/statistics.component';
import { UsersListComponent } from './pages/dashbord-admin/users-list/users-list.component';
import { AddUserComponent } from './pages/dashbord-admin/add-user/add-user.component';
import { DeleteUserComponent } from './pages/dashbord-admin/delete-user/delete-user.component';
import { DetailsUserComponent } from './pages/dashbord-admin/details-user/details-user.component';
import { EditUserComponent } from './pages/dashbord-admin/edit-user/edit-user.component';
import { StatComponent } from './pages/dashbord-directuer/stat/stat.component';
import { DetailsProjectComponent } from './pages/dashbord-directuer/details-project/details-project.component';
import { DetailsTacheComponent } from './pages/dashbord-directuer/details-tache/details-tache.component';



import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { TagModule } from 'primeng/tag';
import { CalendarModule } from 'primeng/calendar';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { ToastModule } from 'primeng/toast';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { TreeTableModule } from 'primeng/treetable';
import { CheckboxModule } from 'primeng/checkbox';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BadgeModule } from 'primeng/badge';
import { MeterGroupModule } from 'primeng/metergroup';
import { ListProjetComponent } from './pages/dashbord-directuer/list-projet/list-projet.component';
import { AddProjetComponent } from './pages/dashbord-directuer/add-projet/add-projet.component';
import { AddTacheComponent } from './pages/dashbord-directuer/add-tache/add-tache.component';
import { RatingModule } from 'primeng/rating';
import { MessageService,ConfirmationService } from 'primeng/api';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { FloatLabelModule } from 'primeng/floatlabel';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { DialogModule } from 'primeng/dialog';
import { PickListModule } from 'primeng/picklist';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ChartModule } from 'primeng/chart';
import { StepperModule } from 'primeng/stepper';
import { StepsModule } from 'primeng/steps';
import { OrderListModule } from 'primeng/orderlist';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayOutComponent,
    DashbordAdminComponent,
    HomeComponent,
    DashbordDirectuerComponent,
    DashbordEmployeeComponent,
    StatisticsComponent,
    UsersListComponent,
    AddUserComponent,
    DeleteUserComponent,
    DetailsUserComponent,
    EditUserComponent,
    ListProjetComponent,
    AddProjetComponent,
    AddTacheComponent,
    StatComponent,
    DetailsProjectComponent,
    DetailsTacheComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ButtonModule,
    TableModule,
    DropdownModule,
    MultiSelectModule,
    TagModule,
    BadgeModule,
    CalendarModule,
    MeterGroupModule,
    IconFieldModule,
    InputIconModule,
    AvatarModule,
    AvatarGroupModule,
    ToastModule,
    ToggleButtonModule,
    TreeTableModule,
    CheckboxModule,
    BrowserAnimationsModule,
    InputTextModule,
    RatingModule,
    SelectButtonModule,
    InputSwitchModule,
    InputTextareaModule,
    InputGroupModule,
    InputGroupAddonModule,
    FloatLabelModule,
    ConfirmPopupModule,
    OverlayPanelModule,
    DialogModule,
    PickListModule,
    DragDropModule,
    ChartModule,
    StepperModule,
    StepsModule,
    OrderListModule
    
  ],
  providers: [
    provideClientHydration(),
    {
      provide:HTTP_INTERCEPTORS,
      useClass:LoginInterceptor,
      multi:true
    },MessageService,ConfirmationService
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
