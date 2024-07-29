import { TestBed } from '@angular/core/testing';

import { StatNbrRolesService } from './stat-nbr-roles.service';

describe('StatNbrRolesService', () => {
  let service: StatNbrRolesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatNbrRolesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
