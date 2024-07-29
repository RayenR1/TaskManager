import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashbordEmployeeComponent } from './dashbord-employee.component';

describe('DashbordEmployeeComponent', () => {
  let component: DashbordEmployeeComponent;
  let fixture: ComponentFixture<DashbordEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashbordEmployeeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashbordEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
