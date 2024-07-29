import { TestBed } from '@angular/core/testing';

import { StatNbrOnlineService } from './stat-nbr-online.service';

describe('StatNbrOnlineService', () => {
  let service: StatNbrOnlineService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatNbrOnlineService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
