import { TestBed } from '@angular/core/testing';

import { AffecterService } from './affecter.service';

describe('AffecterService', () => {
  let service: AffecterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AffecterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
