from unittest import TestCase
import randomized_input
import compute_highest_affinity


class StandAloneTests(TestCase):
	def test_compute1(self):
		num_lines = 10000
		num_users = 1000

		site_list = randomized_input.randomized_site_list(num_lines)
		user_list = randomized_input.randomized_user_list(num_lines, num_users)
		time_list = xrange(0,num_lines)

		computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
		expected_result = ("facebook", "google")
		if computed_result == expected_result:
			self.assertTrue(True)
		else:
			self.assertTrue(False) 

	# def test_compute2(self):
	# 	a = "a"
	# 	b = "b"
	# 	c = "c"
	# 	d = "d"
	# 	e = "e"
	# 	f = "f"
	# 	g = "g"
	# 	h = "h"

	# 	A = "A"
	# 	B = "B"
	# 	C = "C"
	# 	D = "D"
	# 	E = "E"
	# 	F = "F"
	# 	G = "G"
	# 	H = "H"


	# 	site_list = [a,a,b,c,c,c,d,d,e,e,f,f,g]
	# 	user_list = [B,D,C,A,B,C,B,C,C,D,A,B,A]

	# 	time_list = range(0,13)

	# 	computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
	# 	expected_result1 = (c, d)
	# 	expected_result2 = (c, f)

	# 	if computed_result == expected_result1 or computed_result == expected_result2:
	# 		self.assertTrue(True)
	# 	else:
	# 		self.assertTrue(False)