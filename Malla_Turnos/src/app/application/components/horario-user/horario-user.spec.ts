import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorarioUser } from './horario-user';

describe('HorarioUser', () => {
  let component: HorarioUser;
  let fixture: ComponentFixture<HorarioUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HorarioUser],
    }).compileComponents();

    fixture = TestBed.createComponent(HorarioUser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
