import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsTacheComponent } from './details-tache.component';

describe('DetailsTacheComponent', () => {
  let component: DetailsTacheComponent;
  let fixture: ComponentFixture<DetailsTacheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailsTacheComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsTacheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
