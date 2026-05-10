/**
 * name: DDMATH
 * desc: internal API class for DDMath functions
 *
 * author: azrael-x86
 * date: 05092026
 *
 * ૮ ․ ․ ა
 */

package com.azdev.ddtoolsax.ddmath

/////////////
// IMPORTS //
/////////////

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.cos

/////////////
// CLASSES //
/////////////

/** SurveyPoint
 *  represents one survey point
 *
 *  @param measuredDepth measured depth of the survey point
 *  @param inclination angle of the borehole from vertical, measured in degrees (0 is straight down, 90 is horizontal)
 *  @param azimuth direction of the wellbore on the horizontal plane, measured in degrees (0 to 360 from NORTH)
 */
class SurveyPoint(var measuredDepth: Double = 0.0, var inclination: Double = 0.0, var azimuth: Double = 0.0)

/** DogLeg
 *  represents a static DogLeg between two survey points
 *
 *  @param angle dogleg angle
 *  @param severity dogleg severity
 *  @param surveyPoint1 first survey point
 *  @param surveyPoint2 second survey point
 */
class DogLeg(var angle: Double = 0.0, var severity: Double = 0.0, var surveyPoint1: SurveyPoint, var surveyPoint2: SurveyPoint)

class DDMath {
    companion object {

        //////////////////////////////
        // DEGREE/RADIAN CONVERSION //
        //////////////////////////////

        /** CVRADS
         *  converts degrees to radians
         *
         *  @param degrees angle in degrees
         *
         *  @return angle in radians
         */
        fun CVRADS(degrees: Double): Double {
            return degrees * (PI / 180)
        }

        /** CVDEGS
         *  converts radians to degrees
         *
         *  @param radians angle in radians
         *
         *  @return angle in degrees
         */
        fun CVDEGS(radians: Double): Double {
            return radians * (180 / PI)
        }

        ////////////////////
        // BASE FUNCTIONS //
        ////////////////////

        /** CRSLENGTH
         *  calculates distance between two survey depths
         *
         *  @param crSP current survey point
         *  @param pvSP previous survey point
         *
         *  @return difference between surveyPoint1 and surveyPoint2 measured depth
         */
        fun CRSLENGTH(crSP: SurveyPoint, pvSP: SurveyPoint): Double {
            return abs(pvSP.measuredDepth - crSP.measuredDepth)
        }

        //////////////////////
        // DOGLEG FUNCTIONS //
        //////////////////////

        /** DOGLEGANGLE
         *  calculates the angle of wellbore trajectory between two survey points
         *
         *  @param crSP current survey point
         *  @param pvSP previous survey point
         *
         *  @return total change in angle between two survey points
         */
        fun DOGLEGANGLE(crSP: SurveyPoint, pvSP: SurveyPoint): Double {
            var dlAn = 0.0;

            var I1 = CVRADS(pvSP.inclination)
            var I2 = CVRADS(crSP.inclination)
            var A1 = CVRADS(pvSP.azimuth)
            var A2 = CVRADS(crSP.azimuth)

            var P1 = (sin(I1) * sin(I2))
            var P2 = (cos(A2-A1))
            var P3 = (cos(I1) * cos(I2))

            dlAn = acos((P1 * P2) + P3)

            return CVDEGS(dlAn)
        }

        /** DOGLEGSEV
         *  calculates the severity of wellbore trajectory between two survey points in degrees per 100 ft
         *
         *  @param crSP current survey point
         *  @param pvSP previous survey point
         *
         *  @return rate of change expressed as degrees per 100 feet of wellbore trajectory
         */

        fun DOGLEGSEV(crSP: SurveyPoint, pvSP: SurveyPoint): Double {
            val dCL = CRSLENGTH(crSP, pvSP)
            val dA = DOGLEGANGLE(crSP, pvSP)

            return ((dA/dCL) * 100)
        }
    }
}
