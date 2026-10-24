# FTC BIOBUZZ — TeamCode

TeleOp code for the 2026–2027 FTC season (**BIOBUZZ**).

## Contents

| File | OpMode | What it does |
|---|---|---|
| `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecanumDrive.java` | `MecanumDrive` (group: Competition) | 4-wheel mecanum drive, adjustable speed, intake motor, one servo |

## Hardware map

| Device name | Type | Notes |
|---|---|---|
| `frontLeft` | DcMotor | direction REVERSED |
| `frontRight` | DcMotor | forward |
| `backLeft` | DcMotor | direction REVERSED |
| `backRight` | DcMotor | forward |
| `intake` | DcMotor | reversible |
| `servo` | Servo | positions 0.2 / 0.8 |

Both left-side motors are reversed because they are mounted mirror-image to the right side.

## Controls

- **Left stick** — drive (forward/back + strafe)
- **Right stick X** — rotate
- **Left bumper** — decrease drive speed by 10% (range 20%–100%, starts at 60%)
- **Right bumper** — increase drive speed by 10%
- **Right trigger** — intake in, proportional to trigger pull
- **Left trigger** — intake out
- **A** — servo to 0.2
- **B** — servo to 0.8
- **Telemetry** — current drive speed

## Drive maths

Standard mecanum mixing, then normalised by the largest wheel power so no value exceeds 1.0
(the usual "divide by max" trick that prevents the sticks from clipping the output):

```
fl = y + x + rx      fr = y - x - rx
bl = y - x + rx      br = y + x - rx
```

## How to use

This is the `TeamCode` module only. Copy the folder into a new FTC Android Studio project
(SDK: `FtcRobotController`), or drop the `.java` file into
`TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` of your existing project, then
configure the device names above in the Driver Station under *Configure Robot*.

## Notes

- Edge detection for the bumpers compares `left_bumper` against `!lastR1` (and vice versa) in
  the original version. It works, but the pairs are crossed; using `!lastL1` and `!lastR1`
  respectively makes each change fire exactly once per press.
- Speed is clamped with `Range.clip`, so holding a bumper will not push it past 20% or 100%.
