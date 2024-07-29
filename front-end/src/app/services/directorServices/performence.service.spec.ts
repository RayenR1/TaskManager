import { TestBed } from '@angular/core/testing';

import { PerformenceService } from './performence.service';

describe('PerformenceService', () => {
  let service: PerformenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerformenceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
