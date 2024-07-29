import { TestBed } from '@angular/core/testing';

import { DetailsTacheService } from './details-tache.service';

describe('DetailsTacheService', () => {
  let service: DetailsTacheService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetailsTacheService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
