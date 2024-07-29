import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashbordDirectuerComponent } from './dashbord-directuer.component';

describe('DashbordDirectuerComponent', () => {
  let component: DashbordDirectuerComponent;
  let fixture: ComponentFixture<DashbordDirectuerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashbordDirectuerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashbordDirectuerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
