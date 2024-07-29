import { TestBed } from '@angular/core/testing';

import { StatNbrUsresService } from './stat-nbr-usres.service';

describe('StatNbrUsresService', () => {
  let service: StatNbrUsresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatNbrUsresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
