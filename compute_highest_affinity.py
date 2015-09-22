__author__ = 'Jiaolong_Yu'

# No need to process files and manipulate strings - we will
# pass in lists (of equal length) that correspond to
# sites views. The first list is the site visited, the second is
# the user who visited the site.

# See the test cases for more details.


def highest_affinity(site_list, user_list, time_list):
  # Returned string pair should be ordered by dictionary order
  # I.e., if the highest affinity pair is "foo" and "bar"
  # return ("bar", "foo").

    d = dict()                                  # d[site1]=[user1,user2......]
    s_list=list()                               # record all sites for later use
    for i in range(0,len(site_list)):          # implement d and s_list
        if site_list[i] not in d:
            d[site_list[i]]= [user_list[i]]
            s_list.append(site_list[i])
        else:
            d[site_list[i]].append(user_list[i])

    affd = dict()                                # affinity dictionary: tuple(site1,site2) as key, user number as value
    for i in range(0,(len(s_list)-1)):          # implement affd
        for j in range(i+1,len(s_list)):
            affd[(s_list[i],s_list[j])] =0
            for k in range(0,len(d[s_list[i]])):
                if d[s_list[i]][k] in d[s_list[j]]:
                    if (s_list[i],s_list[j]) in affd:
                        affd[(s_list[i],s_list[j])] += 1

    max_tup=(s_list[0],s_list[1])                         # initial the tuple record the highest affinity
    for i in range(0,(len(s_list)-1)):
        for j in range(i+1,len(s_list)):
            if affd[(s_list[i],s_list[j])] >= affd[max_tup]:   # update highest affinity every loop
                max_tup = (s_list[i],s_list[j])
    # sort the tuple items
    mt=list(max_tup)
    mt.sort()
    max_tup=tuple(mt)
    return max_tup
<<<<<<< HEAD

=======
>>>>>>> 84114027e5ca7e4132dc0e5f4422a795522c264f
