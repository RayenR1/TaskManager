import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayOutComponent } from './lay-out.component';

describe('LayOutComponent', () => {
  let component: LayOutComponent;
  let fixture: ComponentFixture<LayOutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LayOutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LayOutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
