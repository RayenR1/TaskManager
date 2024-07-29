import { TestBed } from '@angular/core/testing';

import { AutoAffectService } from './auto-affect.service';

describe('AutoAffectService', () => {
  let service: AutoAffectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutoAffectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
